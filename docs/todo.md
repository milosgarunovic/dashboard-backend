# Todo

## In progress

1. Best way to configure for docker and local development?
2. deployment - docker (jib)

---

1. add hibernate
2. add roles text array field for each user and use roles for role based authorization
3. customize liquibase
   how to run liquibase if there are multiple instances of a server?

https://cheatsheetseries.owasp.org/cheatsheets/Input_Validation_Cheat_Sheet.html

https://owasp.org/www-community/OWASP_Validation_Regex_Repository

--- 

at some later point in time

1. add readme file
2. request timeout
3. monitoring
4. graceful shutdown
5. audit log (check API Security in Action 1.5.4 and chapter 3)
6. rate limiting unauthenticated requests to prevent DoS
7. How and what to log properly. Keep in mind that this is meant to be used in docker, and without docker only for dev

---

for test, check also maximum inputs for some (or all) fields to prevent DoS
for example limit password between 8 and 256 chars

---

if the app calls 3rd party services, we need to log req/res

---

add photos or large emoji next to tasks in order to have visual aid
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

- maybe make browser extension or new page like on new tab
- make a kotlin native app for notifications that lead to website. At some point with WASM maybe it can be everything i
  need
- multiple timers (pomodoro), sync on pause and every 5 seconds (/w backend)

- for pomodoro maybe make a "reverse" pomodoro - Flowmodoro, where you start a timer and work for some time, once you
  are
  feeling tired stop the stopwatch, divide by 5 and take that many minutes of rest, best focusing on something else
  instead of work (like playing an instrument, reading a book, chatting with somebody)

journal on habits and tasks, link those like jira links bugs
https://freedom.to/blog/paper-or-digital-planner/?utm_source=email&utm_campaign=nwslttr81

---

for to do, make a list of things to do this week, and on the end "next week" and just put things for next week there.
Like when you don't know which day next week you're going to do something, just put it there, and on the end of the
week just put in the corresponding day..

Maybe something like

| This week    | Monday      | Tue...      | Next week       |
|--------------|-------------|-------------|-----------------|
| Do this week | Monday task | other tasks | next week tasks |

This week for things that I don't know when I'm going to do. Next week is automatically transferred to this week when
next week starts.

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

- za habits izdvojiti nesto kao prebacivanje iz jedne u drugu teglu "spajalice", kao sto je u knjizi atomic habits
  misao o ovome nakon nekog vremena: mozda napisati u procentima dokle sam stigao sa navikom danas, npr ako je navika
  100 sklekova dnevno, da ih dopunjavam pa dokle stignem
- staviti koji je minimum necega na dnevnom, nedeljnom, mesecnom nivou za uraditi
  isto napraviti da ako prekrism jedan dan, da je to ok, ali ako prekrsim vise dana da to resetuje neki brojac
- napraviti kalendar na kom se vide navike (kojim danima i koliko su uradjene, mozda cak i voditi racuna o vremenu
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

[ ] smisliti kako raditi migracije (po potrebi) i kako ih pokretati najbolje

---

Music part, add songs I'm playing, wishlist (something like for books as well). So I can have all the songs and
everything in one place.

---

[ ] Add tests for weight

---

nomie6 has some great ideas so pick some stuff from there, like widgets

also Lunatask

---

When I get a little more into the app, make a backup feature per user, so it can download a zip of everything he owns.
This can help if I model the app to be "sold" for hosting, like 3-5$ per month, and users move from open source to my
version or vice versa.

---

https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/

Test containers with rest assured

---

Feature idea:

Something like a simple list for all the courses I have, to have them all in one place. For example the structure would
be:

```
Course(
    val name: String,
    val creator: String, // or author or something like that
    val status: Status, // IN PROGRESS, DONE
    val wishlist: Boolean, // courses on wishlist
    val dateBought: Date,
    val dateCompleted: Date,
    val tags: String, // like SBL, Music... easier to group it together with tags
    val category: String, // like Music, Programming, Self-development...
    val location: String // can be an url, location on drive etc
)
```

---

Za kalendar, mozda bi bilo dobro napraviti filtere. Na primer ako imamo podelu "licni zivot" i "posao", da kada sam na
poslu, mogu da oznacim da mi se prikazu samo ti taskovi, dok su za "licni zivot" kao jedan blok oznaceni, bez detalja.
Isto i obrnuto, kada nisam na poslu, da se prikazuje posao kao 1 blok ali bez detalja. Ovo podrazumeva da mogu da pravim
blok unutar bloka, na primer

Posao:

- commute do posla
- sastanak
- slobodno vreme/rucak
- rad na x y z
- commude kući

Ovo bi bilo 5 blokova sve pod jednim blokom posao.

Mozda bih mogao filtrirati i navike, taskove, sastanke itd, da stavim nesto poput "tip" na blok u kalendaru.