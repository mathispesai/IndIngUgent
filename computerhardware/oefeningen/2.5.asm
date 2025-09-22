org 0000H

jmp main

org 0050H

main: mov TMOD,#10H ; timer 1 mode 1 (16 bit timer)
      mov TH1,#63H
      mov TL1,#0C0H
      mov R2,#00
      mov R3,#00
      setb TR1
start:mov R7,#8
loop: jnb TF1,$
      mov TH1,#63H
      mov TL1,#0C0H
      clr TF1
      djnz R7,loop
      inc R2
      cjne R2,#10,uitschrijven
      mov R2,#00
      inc R3
      cjne R3,#10,uitschrijven
      mov R3,#00
uitschrijven: mov A,R3
              swap A
	      orl A,R2
              cpl A
              mov P1,A
      jmp start
      