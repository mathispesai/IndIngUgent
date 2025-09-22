;update frequentie 100
org 0000H

jmp main

org 0050H

main: mov A,#00
loop: mov P1,A
      mov R2,#255
      djnz R2,$
      cpl A
      jmp loop