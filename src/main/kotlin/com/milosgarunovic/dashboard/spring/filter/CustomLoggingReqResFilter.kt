package com.milosgarunovic.dashboard.spring.filter

import kotlinx.datetime.Clock
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.UnsupportedEncodingException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// TODO put before auth filter
open class CustomLoggingReqResFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestStartTime = Clock.System.now()

        val contentCachingRequestWrapper = wrapRequest(request)
        val contentCachingResponseWrapper = wrapResponse(response)
        val reqId = UUID.randomUUID().toString()
        val uri = request.requestURL
        val method = request.method

        var userLogInfo = " user=[/@${request.remoteAddr}];"
        if (SecurityContextHolder.getContext().authentication !is AnonymousAuthenticationToken) {
            val principal = SecurityContextHolder.getContext().authentication.principal as String
            userLogInfo = " user=[${principal}@${request.remoteAddr}];"
        }

        logger.info("-> REQ id=[$reqId];${userLogInfo} url=[$uri]; method=[$method];")
        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper)

        val elapsedRequestTime = (Clock.System.now() - requestStartTime).inWholeMilliseconds
        val status = contentCachingResponseWrapper.status
        val statusText = HttpStatus.valueOf(status).reasonPhrase.uppercase()
        logger.info("<- RES id=[$reqId];${userLogInfo} url=[$uri]; method=[$method]; status=[$status $statusText]; elapsedTime=[${elapsedRequestTime}ms];")

        // must be after filter, because contentCachingRequestWrapper doesn't have anything cached if inputStream is
        // not called
//        beforeRequest(contentCachingRequestWrapper)
//        afterRequest(contentCachingResponseWrapper)
        contentCachingResponseWrapper.copyBodyToResponse()
    }

    private fun beforeRequest(request: ContentCachingRequestWrapper) {
        if (logger.isInfoEnabled) {
            logRequestBody(request)
        }
    }

    private fun afterRequest(response: ContentCachingResponseWrapper) {
        if (logger.isInfoEnabled) {
            logResponseBody(response)
        }
    }

    private fun logRequestBody(request: ContentCachingRequestWrapper) {
        logContent(request.contentAsByteArray, request.characterEncoding)
    }

    private fun logResponseBody(response: ContentCachingResponseWrapper) {
        logContent(response.contentAsByteArray, response.characterEncoding)
    }

    private fun logContent(content: ByteArray, contentEncoding: String) {
        if (content.isEmpty()) {
            return
        }

        try {
            val contentString = java.lang.String(content, contentEncoding)
            logger.info(contentString)
        } catch (e: UnsupportedEncodingException) {
            logger.info("[${content.size} bytes content]")
        }
    }

    private fun wrapRequest(httpServletRequest: HttpServletRequest): ContentCachingRequestWrapper {
        return if (httpServletRequest is ContentCachingRequestWrapper) {
            httpServletRequest
        } else {
            ContentCachingRequestWrapper(httpServletRequest)
        }
    }

    private fun wrapResponse(httpServletResponse: HttpServletResponse): ContentCachingResponseWrapper {
        return if (httpServletResponse is ContentCachingResponseWrapper) {
            httpServletResponse
        } else {
            ContentCachingResponseWrapper(httpServletResponse)
        }
    }
}