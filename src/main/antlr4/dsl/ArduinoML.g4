grammar ArduinoML;

test: 'test' anothertest+ EOF;

anothertest : integer=INT;




Spaces : [ \t\r\n.] -> channel(HIDDEN);
Space :' ' -> channel(HIDDEN);
INT :  [0-9];
Letter : [a-zA-Z];