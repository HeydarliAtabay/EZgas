#opening the browser Google Chrome 
chrome = r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
App.open(chrome)
#new tab in chrome
keyDown(Key.CTRL)
type("t")
keyUp(Key.CTRL)

#type the link for the EZGas application
type("1590918261382.png","http://localhost:8080/")
type(Key.ENTER)
wait(1.5)

#login to the existing account
click("1590920825446.png")
wait(1.5)
emailaddress="s277000@studenti.polito.it"
type("1590931076756.png",emailaddress)
type("1590931118305.png","123456")
click("1590931144558.png")
wait(1.5)

#opening the user modification page
click("1590931203304.png")
#modifying the old password
newpassword="123456polito"
type("1590931258601.png",newpassword)
click("1590931286852.png")
wait(1.5)

#checking if the password is changed
click("1590931426419.png") #logout from account
wait(1)
click("1590920825446.png")
type("1590931076756.png",emailaddress)
type("1590931118305.png",newpassword)
click("1590931144558.png")
find("1590931588063.png")  # searching for that part, because if user successfully loged
#in to the system, we should see greetings!
wait(1)

