org 0000H

mov R0,#5
push 00H
call facul
dec SP
jmp $

facul: push 00H
       mov R0,SP
       push B
       push 02H
       dec R0
       dec R0
       dec R0
       mov 02,@R0    ;R2 bevat n
       cjne R2,#0, recursie
       ;R2==0
       mov A,#1
       pop 02H
       pop B
       pop 00H
       ret
recursie: dec R2  ; n=n-1
          push 02H ; n-1 --->stack
          call facul
          ;accumulator bevat faculteit van n-1
          dec SP
          inc R2  ;n heeft terug de juiste waarde
          mov B,R2
          mul AB
          pop 02H
          pop B
          pop 00H
          ret