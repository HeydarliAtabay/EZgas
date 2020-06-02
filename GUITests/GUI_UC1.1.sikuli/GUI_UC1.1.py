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

# Test case for UC1.1
click("1590920825446.png")
wait(1.5)
click("1590921696540.png")
wait(1.5)
type("1590921759771.png","Atabay")
type("1590922249954.png","s277000@studenti.polito.it")
type("1590921833338.png","123456")
click("1590921889795.png")

#Visual assertion- Searching for Login button, as 
#after successful creation of user the login screen is appeared
find("1590925618200.png")
wait(2)


