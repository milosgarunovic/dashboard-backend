1. add unit tests and integration tests with testcontainers
   1.1. validate everything (user registration duplicate username for example)
2. add hibernate
3. add roles text array field for each user and use roles for role based authorization
4. customize liquibase
   how to run liquibase if there are multiple instances of a server?

https://cheatsheetseries.owasp.org/cheatsheets/Input_Validation_Cheat_Sheet.html

https://owasp.org/www-community/OWASP_Validation_Regex_Repository

--- 

at some later point in time

1. add readme file
2. request timeout
3. monitoring
4. deployment - docker (jib)
5. graceful shutdown
6. audit log (check API Security in Action 1.5.4 and chapter 3)
7. rate limiting unauthenticated requests to prevent DoS

---

for test, check also maximum inputs for some (or all) fields to prevent DoS
for example limit password between 8 and 256 chars

---

if the app calls 3rd party services, we need to log req/res

---

add photos next to tasks in order to have visual aid
found on "I'll remember" app

---

maybe make it "invite-only", so the admin must invite people in and nobody else can join without the invitation

jenkins has this functionality - "allow users to sign up" in configure security

---

security
add kid header for key rotation
it is a common security practice to invalidate all of a user’s existing sessions when they change their password
RBAC - Role Based Access Control - need to implement this when I get more than one role for user

---

maybe make browser extension or new page like on new tab
make a (flutter) app for notifications
multiple timers (pomodoro), sync on pause and every 5 seconds (/w backend)

for pomodoro maybe make a "reverse" pomodoro - Flowmodoro, where you start a timer and work for some time, once you are
feeling tired stop the stopwatch, divide by 5 and take that many minutes of rest, best focusing on something else
instead of work (like playing an instrument, reading a book, chatting with somebody)

journal on habits and tasks, link those like jira links bugs
https://freedom.to/blog/paper-or-digital-planner/?utm_source=email&utm_campaign=nwslttr81

---

for to do, make a list of things to do this week, and on the end "next week" and just put things for next week there.
Like when you don't know which day next week you're going to do something, just put it there, and on the end of the
week just put in the corresponding day..

---

https://www.makeuseof.com/productivity-apps-to-make-goals-reality/ copy some of the ideas that fit

---

add swagger/open API

---

[ ] make it "smart" by typing for example "[today]", "[tomorrow]", "[tomorrow at 6PM]" etc, in [], for program to
understand that it's a command
[ ] maybe find online projects that have already done this and copy some of the UI
[ ] napraviti dodatak za tasks sa strane, kao drugu kolonu, da bude quotes
https://www.entrepreneur.com/article/394474

---

* za habits izdvojiti nesto kao prebacivanje iz jedne u drugu teglu "spajalice", kao sto je u knjizi atomic habits
  misao o ovome nakon nekog vremena: mozda napisati u procentima dokle sam stigao sa navikom danas, npr ako je navika
  100 sklekova dnevno, da ih dopunjavam pa dokle stignem
* staviti koji je minimum necega na dnevnom, nedeljnom, mesecnom nivou za uraditi
  isto napraviti da ako prekrism jedan dan, da je to ok, ali ako prekrsim vise dana da to resetuje neki brojac
* napraviti kalendar na kom se vide navike (kojim danima i koliko su uradjene, mozda cak i voditi racuna o vremenu
  pocetka/zavrsetka kako bih znao kad mi najvise odgovara neka navika)

---

mozda za pocetak napraviti integraciju sa google calendar da se vide taskovi od moje app i google calendar, pa posle
napraviti opciju da ima built in kalendar

---

https://crushentropy.com/plan

---

world clock - add clocks and maybe write who's there, for example know what time it's at Logan's place, and how much is
that relative to my time (+ / - from my time)

---

add klint, qodona, docker, test coverage percentage

---

https://www.iqair.com/air-pollution-data-api

idea for selfhosted - air pollution api use it and make graphics or import to grafana?

same for weather - so I can have a dashboard with weather and air pollution

---

[ ] IN PROGRESS - pomeriti auth stvari u auth.jwt paket i napisati testove za to, mozda cak i na nivou http servera
[ ] smisliti kako raditi migracije (po potrebi) i kako ih pokretati najbolje