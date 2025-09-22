org 0000H

mov 70H,#1
mov 71H,#2
mov 72H,#3
mov 73H,#7
mov 74H,#15
mov 75H,#23H
mov A,#7
push Acc
mov A,#6
push Acc
mov A,#70H
push Acc
call zoek    ; na die aanroep zal Acc (of A) de returnwaarde bevatten
cjne A,#1,naar_een
;gelijk aan 1
clr P1.6
jmp $
naar_een: setb P1.6
          jmp $

zoek: 
     ;----------
     ;MSB oude PC  <---- SP
     ;----------
     ;LSB oude PC
     ;----------
     ;70H
     ;----------
     ;6
     ;----------
     ;7
     push 00H
     mov R0,SP
     push 01H
     push 02H
     push 03H
     push 04H
     ;oude R4       <---- SP
     ;----------  
     ;oude R3       
     ;----------  
     ;oude R2           
     ;----------
     ;oude R1        
     ;----------     
     ;oude R0        <----R0
     ;----------
     ;MSB oude PC  
     ;----------
     ;LSB oude PC
     ;----------
     ;70H
     ;----------
     ;6
     ;----------
     ;7
     dec R0
     dec R0
     dec R0
     ;oude R4     <---- SP
     ;----------
     ;oude R3       
     ;----------  
     ;oude R2           
     ;----------
     ;oude R1        
     ;----------     
     ;oude R0        
     ;----------
     ;MSB oude PC  
     ;----------
     ;LSB oude PC
     ;----------
     ;70H          <----R0
     ;----------
     ;6
     ;----------
     ;7
     mov R2,#0      ;R2=0
     mov 01,@R0     ;R1=70H
     dec R0     
     ;oude R4     <---- SP      
     ;----------
     ;oude R3       
     ;----------  
     ;oude R2           
     ;----------
     ;oude R1        
     ;----------     
     ;oude R0        
     ;----------
     ;MSB oude PC  
     ;----------
     ;LSB oude PC
     ;----------
     ;70H          
     ;----------
     ;6            <----R0
     ;----------
     ;7
     mov 03H,@R0  ;R3=6
     dec R0
     mov 04H,@R0  ;R4=7
     mov A,R2     ;A bevat kopie van i 
start: cjne A,03H,verschillend
     ;A==R3 => stoppen
     mov A,#0   ; returnwaarde 0
     pop 04H
     pop 03H
     pop 02H
     pop 01H
     pop 00H
     ret 
verschillend: mov A, R1  ;A=startadres
              add A,R2   ;A=startadres+i
              mov R0,A   ; R0 bevat adres van i-de element van array
              mov A,@R0  ; A bevat inhoud van i-de element van array
              cjne A, 04H, verder
              ;A==R4 => getal gevonden!
              mov A,#1   ; returnwaarde 1
     	      pop 04H
              pop 03H
              pop 02H
              pop 01H
              pop 00H
              ret
verder: inc R2    ;i++
        mov A,R2  ;while herhalen
        jmp start

