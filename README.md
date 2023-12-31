# Online Store Backend
Project created at JavaSchool 2023, this repository saves my API for my onlineStore. It's a fully functional API using Spring and maven, it is docker-file and it includes tests to check the code


# How to deploy
Before deploying, ensure you've downloaded both this repository and the **frontend** repository: [OnlineStoreFrontend](https://github.com/carlokos/OnlineStoreFrontend.git). 

Once you have both repositories on your machine, organize the directories correctly according to the `docker-compose.yml`. Please check the provided `docker-compose.yml` for guidance
```
- Backend
-- Backend-files
- Any name
-- Frontend
---- Frontend-files
```

# How to use it

After setting up, run the following command: `docker-compose up`. No need to build any app since both repositories come with their respective builds. 
- Backend runs on port 8080.
- Frontend runs on port 5173.

Access the web app at [localhost:5173](http://localhost:5173) and start exploring. The database includes an admin user: 
- Email: admin@admin.com 
- Password: root 

The docker container is call `onlinestore`
Hope you enjoy navigating through my OnlineStore!

# Characteristics
- Real time updates with cart, orders and products stock
- Secure login and sign up with scripting password
- Admin privileges 
- All in a docker container to easy use
- Tests codes for controllers and services
