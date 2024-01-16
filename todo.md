1. add unit tests and integration tests with testcontainers
   1.1. validate everything (user registration duplicate username for example)
2. add hibernate
3. add roles text array field for each user and use roles for role based authorization
4. migrate to Spring WebFlux (with coroutines)
5. customize liquibase
   how to run liquibase if there are multiple instances of a server?

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

how to save req/res for local testing, because everything should be inside the project. Maybe like a postman collection?

add integration tests as well as unit tests (testcontainers...)

add klint, qodona, docker, test coverage percentage

---

[IN PROGRESS] pomeriti auth stvari u auth.jwt paket i napisati testove za to, mozda cak i na nivou http servera
[ ] dodati users tabelu i vezati note-ove za njega
[ ] smisliti kako raditi migracije (po potrebi) i kako ih pokretati najbolje
[ ] add database (postgres plus expose) - podseticu se baza, spremanje za kurs (mana je dodatni softver za odrzavanje)