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


click("1590920825446.png")
wait(1.5)
type("1591058662090.png","s277000@studenti.polito.it")
type("1591058702060.png","12345")
click("1591058923455.png")

click("1591107547509.png")
wheel(WHEEL_DOWN,5)
if exists("1591057377597.png"):
    #making a search with specific data
    type("1591057397518.png","Via Pietro Cossa")
    wait(3)
    mouseMove(0,25)
    click(Mouse.at())
    click(Pattern("1591057468746-1.png").targetOffset(63,2))
    click(Pattern("1591057742948-1.png").targetOffset(-44,14))

    doubleClick("1591057685982.png")
    wheel(WHEEL_DOWN,2)
    wait(3)
    #visual assertion- if the search is successful we should see this part on the screen
    find("1591107232241.png")