	.file	"testc.c"
	.intel_syntax noprefix
	.text
	.globl	f
	.type	f, @function
f:
	push	ebp
	mov	ebp, esp
	sub	esp, 16
        mov     DWORD PTR[ebp-4], 0
	jmp	.L2
.L3:
	add	DWORD PTR [ebp-4], 1
.L2:
	mov	eax, DWORD PTR [ebp+8]
	lea	edx, [eax+1]
	mov	DWORD PTR [ebp+8], edx
	movzx	eax, BYTE PTR [eax]
	test	al, al
	jne	.L3
	mov	eax, DWORD PTR [ebp-4]
	leave
	ret
	.size	f, .-f
	.section	.rodata
.LC0:
	.string	"%d\n"
	.text
	.globl	main
	.type	main, @function
main:
	lea	ecx, [esp+4]
	and	esp, -16
	push	DWORD PTR [ecx-4]
	push	ebp
	mov	ebp, esp
	push	ecx
	sub	esp, 20
	mov	WORD PTR [ebp-11], 25185
	mov	BYTE PTR [ebp-9], 0
	sub	esp, 12
	lea	eax, [ebp-11]
	push	eax
	call	f
	add	esp, 16
	sub	esp, 8
	push	eax
	push	OFFSET FLAT:.LC0
	call	printf
	add	esp, 16
	mov	eax, 0
	mov	ecx, DWORD PTR [ebp-4]
	leave
	lea	esp, [ecx-4]
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 10.3.1 20210422 (Red Hat 10.3.1-1)"
	.section	.note.GNU-stack,"",@progbits
