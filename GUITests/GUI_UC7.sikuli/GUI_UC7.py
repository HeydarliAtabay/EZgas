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
type("1591058702060.png","123456")
click("1591058923455.png")
wait(1.5)

wheel(WHEEL_DOWN,5)
if exists("1591057377597.png"):
    #making a search with specific data
    type("1591057397518.png","Via Pietro Cossa")
    wait(2)
    mouseMove(0,25)
    click(Mouse.at())
    click(Pattern("1591057468746.png").targetOffset(63,2))
    click(Pattern("1591057742948.png").targetOffset(-44,14))
    click(Pattern("1591057582437.png").targetOffset(62,-5))
    click("1591057677187.png")
    click("1591057685982.png")
    
    #adding report for this gas station
    wheel(WHEEL_DOWN,2)
    wait(1)
    click("1591057858976.png")
    wait(1)
    for c in range(2):
        wheel(WHEEL_DOWN,5)
        if exists("1591057980767.png"):
            type("1591058028678.png","1,27")
            type("1591058058194.png","1,45")
            type("1591058077258.png","1,13")
            click("1591058107210.png")
            wait(2)
        #visual assertion, since after successfully added report we should see this message
            find("1591058139869.png")
        else:
            wheel(WHEEL_DOWN,2)
    
     
wait(1)

    