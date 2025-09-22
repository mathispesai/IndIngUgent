;update frequentie 100
org 0000H

jmp main

org 0050H

main: mov R2,#8
      mov A,#0BCH
      mov B,#00
loop: rrc A ; ACC>>=1 en C=LSb Acc 
      push Acc
      mov A,B
      rlc A
      mov B,A ; B<<=1 en LSb B=C
      pop ACC
     djnz R2, loop
     jmp $
             