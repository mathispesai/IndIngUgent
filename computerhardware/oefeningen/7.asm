;update frequentie 100
org 0000H

jmp main

org 0050H

main: clr C ; C==0 => naar links lopen
      
      mov A,#0FEH

loop: mov P1,A
      mov R2,#255
      djnz R2,$
      jnb P2.7,ingedrukt
      jc naar_rechts
      rl A
      jmp loop

ingedrukt: jnb P2.7,$
           cpl C
           jmp loop 

naar_rechts: rr A
             jmp loop                       
      