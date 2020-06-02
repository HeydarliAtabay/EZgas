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
#deleting user1
click("1591041546572.png")
click("1591042687398.png")
wait(1.5)
#deleting user2
click("1591041572123.png")
click("1591042687398.png")
