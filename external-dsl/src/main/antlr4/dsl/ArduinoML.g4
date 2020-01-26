grammar ArduinoML;


/******************
 ** Parser rules **
 ******************/

root            :   declaration bricks states EOF;

declaration     :   'application' name=IDENTIFIER;

bricks          :   (sensor|actuator|lcd|keyboard)+;
    sensor      :   'sensor'   location ;
    actuator    :   'actuator' location ;
    lcd         :   'lcd'      location ;
    keyboard    :   'keyboard' id=IDENTIFIER (':' STRING ',' STRING)?;
    location    :   id=IDENTIFIER ':' port=PORT_NUMBER;

states          :   state+;
    state       :   initial? name=IDENTIFIER '{'  action+ transition+ '}';
    action      :   receiver=IDENTIFIER (actionLCD | actionAssignment);
    actionLCD   :   'print' value=(IDENTIFIER|STRING);
    actionAssignment : '<=' value=(SIGNAL|IDENTIFIER);
    transition  :   (trigger=IDENTIFIER 'is' value=SIGNAL)? '=>' next=IDENTIFIER ;
    initial     :   '->';

/*****************
 ** Lexer rules **
 *****************/

PORT_NUMBER     :   [1-9] | '10' | '11' | '12';
IDENTIFIER      :   LOWERCASE (LOWERCASE|UPPERCASE|DIGIT)+;
STRING          :   '"' IDENTIFIER '"';
SIGNAL          :   'HIGH' | 'LOW';

/*************
 ** Helpers **
 *************/

fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
fragment DIGIT      : [0-9];
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
/** -> channel(HIDDEN); instead of skip if you don't want to parse whitespaces at all.