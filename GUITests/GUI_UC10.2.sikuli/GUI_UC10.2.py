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

# the reputation of user Atabay(user1) who did the report is 2 

#login as Atabay1(User2) for evaluating the report
click("1590920825446.png")
wait(1.5)
type("1591058662090.png","s277000@studenti.polito.it")
type("1591058702060.png","123")
click("1591058923455.png")
wait(1)
click("1591107547509.png")
wheel(WHEEL_DOWN,5)
if exists("1591057377597.png"):
    #making a search with specific data
    type("1591057397518.png","Via Pietro Cossa")
    wait(3)
    mouseMove(0,25)
    click(Mouse.at())
    click("1591109803748.png")
    wheel(WHEEL_DOWN,2)
    wait(3)
    doubleClick("1591129951004.png")
    wait(1)
#after this user reputation of Atabay will decrease from 2 to 1
    