sensor "button" onPin 9
actuator "led" pin 10
lcd "ecranLCD" onBus 2
keyboard "k" values "mathieu","seg"

state "write" means ecranLCD prints k   //Ecran lcd prend la valeur du keyboard
state "ledAssign" means led becomes k //#Led prend la valeur HIGH si on tape "mathieu" sinon la valeur low si on tape seg => comportement par défaut 1/0
initial write

from write to ledAssign

export "lcdDo"
