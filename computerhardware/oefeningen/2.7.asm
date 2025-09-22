org 0000H

jmp main

org 0080H

main:
mov 20H,#0C0H
mov 21H,#0F9H
mov 22H,#0A4H
mov 23H,#0B0H
mov 24H,#99H
mov 25H,#92H
mov 26H,#82H
mov 27H,#0F8H
mov 28H,#80H
mov 29H,#90H

mov TMOD,#01H ; Timer 0 => mode 1 => 16 bit timer
mov TH0,#3CH
mov TL0,#0B0H

setb TR0
mov R2,#00 ; R2 is teller => gebruiken als index in de array
mov P1,20H

loop:  mov R7,#20
start: jnb TF0,$
       mov TH0,#3CH
       mov TL0,#0B0H
       clr TF0
       djnz R7,start
       inc R2
       cjne R2,#10,uitschrijven
       mov R2,#00
uitschrijven: mov A,#20H
              add A,R2
	      mov R0,A    ; enkel R0 of R1 kan gebruikt worden als pointer
              mov P1,@R0  ; indirecte adressering
              jmp loop 