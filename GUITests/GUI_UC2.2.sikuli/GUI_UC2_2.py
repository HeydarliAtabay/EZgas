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
click("1590920825446.png")
wait(1.5)
type("1590929404313.png","admin@ezgas.com")
type("1590929529701.png","admin")
click("1590929576884.png")
wait(1.5)
click("1590929624093.png")
wait(1.5)

#mdifying User1
click("1591041546572.png")
click("1591041369600.png")
click("1591041896909.png")
keyDown(Key.CTRL)
type("a")
keyUp(Key.CTRL)
type(Key.DELETE)
type("1591041948779.png","newpass1")
click("1591041971118.png")

#modifying User2
click("1591041572123.png")
click("1591041369600.png")
click("1591042109377.png")
keyDown(Key.CTRL)
type("a")
keyUp(Key.CTRL)
type(Key.DELETE)
type("1591041948779.png","newpass2")
click("1591041971118.png")
wait (1.5)
wait(1.5)