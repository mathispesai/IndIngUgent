org 0000H

jmp main

org 0050H

main: mov TMOD,#10H ; timer 1 mode 1 (16 bit timer)
      mov TH1,#0B1H
      mov TL1,#0E0H
      mov R2,#00
      setb TR1
start:mov R7,#100
loop: jnb TF1,$
      mov TH1,#0B1H
      mov TL1,#0E0H
      clr TF1
      djnz R7,loop
      inc R2
      cjne R2,#10,uitschrijven
      mov R2,#00
uitschrijven: mov A,R2
              cpl A
              mov P1,A
      jmp start
      