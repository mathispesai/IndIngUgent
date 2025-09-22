;update frequentie 100
org 0000H

jmp main

org 0050H

main: mov A,#0FEH
loop: mov P1,A
      mov R2,#255
      djnz R2,$
      rl A
      jmp loop