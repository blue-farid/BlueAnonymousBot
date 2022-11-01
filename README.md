  # Blue Anonymous Bot
  ![logo](logo.png)
  ## What the hell is this?
  * This is a Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
  * The original idea for the robot came about when we wanted to know who was sending us anonymous messages using 'برنامه ناشناس' bot.
  * SO WE WROTE OUR OWN COPY!
  * (the original bots: @BChatBot - @BiChatbot)

  ## How to use it?
  * clone the repository
  * create a telegram bot
    * open telegram
    * text @botfather and create your bot
    * note the token and the username of the bot
  * in the resource folder, create 'bot_config.properties' file and put the lines below:
      * bot.username=[the username]
      * bot.token=[the token]
      * api.password=[custom_password]
      * is.test=false
  * in the resource folder, set spring.jpa.hibernate.ddl-auto = create.
  * now just execute the 'run' script!
  * if everything work correctly, then stop the app and change spring.jpa.hibernate.ddl-auto = none.
    then execute 'run' again and enjoy!
  * NOTE_1: if telegram is censored in your country, you need a vpn.
  * NOTE_2: you need java 17 and maven installed on your machine.
  
  * ### MAKE YOURSELF ADMIN!
    * for seeing who sends to you anonymous messages directly on telegram, 
      you should set yourself as admin. for this, you should use the api.
      there is file called curl/set-admin.curl. just install curl and then execute it
      on the server. or you can change 'localhost' to the server IP. (you can use postman too).
  
  ## Logging!
  * the application uses slf4j and logs each request.
  * you can find logs on /var/log/blue-anonymous-bot/
  * at the end of the each day, logs saved in separate files in 'logging.log.yyyy/MM/dd.' format
  
  ## Monitor messages!
   * application logs every chats on the separate files.
   * consider A and B clients for example.
   * if A send a message to B (with any command), the message will log in the '/var/log/blue-anonymous-bot/SendMessage/{A.numeric_id}-{B.numeric_id}.txt'.
   * for each two client, we have a file that saves all messages between them.


### fully tested on ubuntu 20.04
