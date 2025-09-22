org 0000H

mov 70H,#1
mov 71H,#2
mov 72H,#3
;parameters op de stack van rechts naar links 

mov R0,#3
push 00H
mov R1,#70H
push 01H
call gle
dec SP
dec SP
jmp $

gle: ;---------
     ;oude PC(MSB)    <--- SP
     ;---------
     ;oude PC(LSB)
     ;---------
     ;70H
     ;---------
     ;3
     push 00H
     mov R0,SP
     ;---------
     ;oude R0     <---R0    <---SP
     ;---------
     ;oude PC(MSB)    
     ;---------
     ;oude PC(LSB)
     ;---------
     ;70H
     ;---------
     ;3
     dec R0
     dec R0
     dec R0
     ;---------
     ;oude R0     <---SP
     ;---------
     ;oude PC(MSB)    
     ;---------
     ;oude PC(LSB)
     ;---------
     ;70H        <---R0
     ;---------
     ;3
     mov A,@R0
     dec R0
     mov 00H,@R0
     add A,R0    ; A=70H+3
     dec A
     mov R0,A    ; R0=70H+3-1
     mov A,@R0   ; A=*(70H+3-1)
     pop 00H
     ret