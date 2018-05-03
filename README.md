# Java Mail
Implementation of E-mail sending function using [Java Mail API](http://www.oracle.com/technetwork/java/index-138643.html)
## How to build
Run the `Main.java` file in the `/src`.  
You are required to fill in the following information:
  - > Who would you like to send the email for?
    - This is the `To` blank. (What you enter should be a legal e-mail address)
  - >  What is your email address?
    - This is the `From` blank. (What you enter should be a legal e-mail address)
  - > Please enter the password of your email.
    - This is the authorization code of your e-mail. Please make sure that POP3/SMTP service has been enabled.
  - > Do you want to send a copy to anyone else? Y/N
    - This is the button to enable `CC`. If you want to enable CC, enter `Y`. Otherwise, `N`.
      - > Please enter email addresses(split with ",")
        - You are supposed to enter a list of e-mail addresses that you intend to CC to, splitting with ",".
  - > Do you want sent a copy blindly? Y/N
    - This is the button to enable `BCC`. If you want to enable BCC, enter `Y`. Otherwise, `N`.
      - > Please enter email addresses(split with ",")
        - You are supposed to enter a list of e-mail addresses that you intend to BCC to, splitting with ",".
  - > What is your subject of the email?
    - This is the `Subject` blank.
  - > Enter the content of your email.
    - This is the `Content` blank.
  - > Do you want to add an attachment? Y/N
    - This is the button to enable `attachment`. If you want to enable attachment,enter `Y`. Otherwise, `N`.
      - > Please enter the absolute path of your file.
        - You are supposed to enter the absolute path of your file on your computer (e.g. `C:\Users\Desktop`).
Then you've done everything. Wait patiently until the mail has been sent.
## License
This work is licensed under a Apache License. Sincerely welcome contribution.
