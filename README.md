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
  * in resource folder, create 'original_config.properites' file and put the two lines below:
      * bot.username=[the username]
      * bot.token=[the token]
  * now just go to out/artifacts/ and run the jar file! now the bot is up.
  * NOTE: if telegram is censored in your country, you need a vpn.
  
  * ### MAKE YOURSELF ADMIN!
    * for seeing more data directly on telegram, you should set yourself as admin
    * so, just on the terminal, type 'set admin [your telegram numeric id] true'
      * how to find out your numeric id? just text to the @RawDataBot. It returns all of your account information beside the id.
    * the admins can see more data than normal users. for example they can see who is sending anonymous messages.
    * some special commands defined just for the admins that you can search for them in the source code (on 'command' package!).
  * you can read javadocs to prevents the possible confussions or contact me directly in telegram.
    * at least, read the documention of 'console.ConsoleReader'. It is necessary for interacting with the bot as an admin.
  
  ## Logging!
  * in new versions, application uses log4j and logs each request.
  * you can see logs directly on console or on the 'logs/logging.log' file.
  * at the end of the each day, logs saved in seprate files in 'logging.log.yyyy/MM/dd.' format
  
  ## Monitor messages!
   * in new versions, application logs every chats on the separate files.
   * consider A and B clients for example.
   * if A send a message to B (with any command), the message will log in the 'files/monitor/SendMessageToContactCommand/{A.numeric_id}-{B.numeric_id}.txt'.
   * for each two client, we have a file that saves all messages between them.


### fully tested on ubuntu 20.04
