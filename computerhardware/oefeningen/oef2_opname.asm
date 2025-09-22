org 0000H

mov R0,#7
push 00H
mov R2,#255
push 02H
call verm
dec SP
dec SP
jmp $
;R2(LSB) R3(MSB) resultaat
;R4 multiplicator
;R6(LSB) R7(MSB) multiplicand

verm: push 00H
      mov R0,SP
      push 02H
      push 03H
      push 04H
      push 05H
      push 06H
      push 07H
      dec R0
      dec R0
      dec R0
      mov 06H,@R0
      mov R7,#0
      dec R0
      mov 04H,@R0
      mov R2,#0
      mov R3,#0
start: mov A,R4
       anl A,#1  ;and logic => A=A&1
       jz verder
       ;A!=1
       ;res+=multiplicand
       mov A,R6
       add A,R2
       mov R2,A
       mov A,R7
       addc A,R3   ;A=A+R3+C
       mov R3,A
verder: clr C
        mov A,R6
        rlc A
        mov R6,A
        mov A,R7
        rlc A
        mov R7,A
        ;multpicator/=2 of multiplicator>>=1
        mov A,R4
        rrc A
        mov R4,A
        clr C
        jnz start
        mov A,R2
        mov B,R3
        pop 07H
        pop 06H
        pop 05H
        pop 04H
        pop 03H
        pop 02H
        pop 00H
        ret


