org 0000H

mov R0,#7
push 00H
mov R1,#255
push 01H
call verm
dec SP
dec SP
jmp $



verm: ;R2 R1 => multiplicand
      ;R4    => multiplicator
      ;R6 R5 => resultaat 

      push 00H
      mov R0,SP
      push 01H
      push 02H
      push 04H
      push 05H
      push 06H
      dec R0
      dec R0
      dec R0
      mov 01H,@R0   ; R1=LSB(multiplicand)
      dec R0
      mov 04H,@R0   ; R4=multiplicator
      mov R2,#0
      mov R6,#0
      mov R5,#0
start: mov A,R4
       clr C
       rrc A 
       mov R4,A       
       jnc verder
       ;C=1
       clr C
       mov A,R1
       add A,R5
       mov R5,A
       mov A,R2
       addc A,R6
       mov R6,A
verder: clr C     ; weg te snoeien
        mov A,R1
        rlc A
        mov R1,A
        mov A,R2
        rlc A
        mov R2,A
        mov A,R4
        jnz start
        mov A,R5
        mov B,R6
        pop 06H
        pop 05H
        pop 04H
        pop 02H
        pop 01H
        pop 00H
        ret
        
        