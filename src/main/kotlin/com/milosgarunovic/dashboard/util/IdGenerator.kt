package com.milosgarunovic.dashboard.util

import io.hypersistence.tsid.TSID

class IdGenerator {
    companion object {
        fun longId(): Long {
            return TSID.Factory.getTsid().toLong()
        }
    }
}