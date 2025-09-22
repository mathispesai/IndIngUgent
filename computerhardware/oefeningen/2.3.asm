org 0000H

jmp main

org 0050H

main: mov TMOD,#10H ; timer 1 mode 1 (16 bit timer)
      mov TH1,#06H
      mov TL1,#00H
      setb TR1
loop: jnb TF1,$
      mov TH1,#06H
      mov TL1,#00H
      clr TF1
      cpl P1.6
      jmp loop
      