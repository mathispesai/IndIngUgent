org 0000H

mov A,#7
mov B,#30
push B
push Acc
call verm
dec SP
dec SP
jmp $

verm: push 00H
      mov R0,SP
      push B
      dec R0
      dec R0
      dec R0
      mov A,@R0
      dec R0
      mov B,@R0
      mul AB
      pop B
      pop 00H
      ret
