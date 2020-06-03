#opening the browser Google Chrome 
chrome = r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
App.open(chrome)
#new tab in chrome
keyDown(Key.CTRL)
type("t")
keyUp(Key.CTRL)

#type the link for the EZGas application
type("1590918261382-1.png","http://localhost:8080/")
type(Key.ENTER)
wait(1.5)

# login as Admin
click("1590920825446-1.png")
wait(1.5)
type("1590929404313.png","admin@ezgas.com")
type("1590929529701.png","admin")
click("1590929576884.png")
wait(1.5)
click("1590929624093.png")
wait(1.5)

newNameOfGasStation="BP"  
for n in range(5):
    if exists("1591054768275.png"):
        click("1591054792710.png")
        wait(0.5)
        wheel(WHEEL_UP,3)
        if exists("1591054851725.png"):
            click(Pattern("1591054974283.png").targetOffset(55,0))
            #deleting the old name of gasstation
            keyDown(Key.CTRL)
            type("a")
            keyUp(Key.CTRL)
            type(Key.DELETE)
            #new name for gas station
            type(Pattern("1591054974283.png").targetOffset(55,0),"Shell")
            click("1591055069471.png")
                #visual assertion, since after succesful updating  all fields have to be empty
            find("1591055112125.png")
            break
        else:
            wheel(WHEEL_UP,1)
        
    else:
        wheel(WHEEL_DOWN, 3)