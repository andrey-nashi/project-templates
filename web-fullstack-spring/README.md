# jv-fullstack-security
An example of a full-stack application using Spring Boot (JWT)+ React

*Install*

Backend (in /backend-jv):
- `gradle build`

Frontend (in /frontend):
- `npm install`

*Run*

- backend: `gradle bootRun`
- frontend: `npm start`

*Description*

- Home page is accessible to everyone
- Login to access user panel (2 users are stored in db: (admin1, pass1), (admin2, pass2))

Note: if the frontend shows an error *Cannot find module '@csstools/normalize.css'* then just copy the folder
from */install* to */frontend/node_modules*