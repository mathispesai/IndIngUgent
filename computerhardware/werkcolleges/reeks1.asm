org 0000H

jmp main

org 0050H

main: mov R2,#0 

start: jb P2.6,$
		jnb P2.6,$
		inc R2
		inc R2
		mov A,R2
		mov R3,A
loop:
		djnz R3, loop
		jmp main
		cpl