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
for n in range(3):
    if exists("1591048903903.png"):
        type("1591043972046.png","Shell")
        type("1591044006208.png","Via Pietro Cossa")
        wait(2)
        mouseMove(0,25)
        click(Mouse.at())
        wait(1)
        click("1591044081838.png")
        click("1591044101982.png")
        for b in range(2):
            if exists("1591048942418.png"):
                for a in range (4): 
                    click("1591044143359.png")
            else: 
                wheel(WHEEL_DOWN,3)
        click("1591044172260.png")    
        break
    else:
        wheel(WHEEL_DOWN, 2)
           
wait(1)