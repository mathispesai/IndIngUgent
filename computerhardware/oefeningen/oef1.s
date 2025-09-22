	.file	"test.c"
	.intel_syntax noprefix
	.text
	.globl	f
	.type	f, @function
f:
	push	ebp
	mov	ebp, esp
	sub	esp, 16
	mov	DWORD PTR [ebp-4], 0
	jmp	.L2
.L5:
	mov	eax, DWORD PTR [ebp+8]
	mov	eax, DWORD PTR [eax]
	cmp	DWORD PTR [ebp+16], eax
	jne	.L3
	mov	eax, 1
	jmp	.L4
.L3:
	add	DWORD PTR [ebp+8], 4
	add	DWORD PTR [ebp-4], 1
.L2:
	mov	eax, DWORD PTR [ebp-4]
	cmp	eax, DWORD PTR [ebp+12]
	jl	.L5
	mov	eax, 0
.L4:
	leave
	ret
	.size	f, .-f
	.globl	main
	.type	main, @function
main:
	push	ebp
	mov	ebp, esp
	sub	esp, 32
	mov	DWORD PTR [ebp-20], 1
	mov	DWORD PTR [ebp-16], 5
	mov	DWORD PTR [ebp-12], 7
	mov	DWORD PTR [ebp-8], 4
	push	7
	push	4
	lea	eax, [ebp-20]
	push	eax
	call	f
	add	esp, 12
	mov	DWORD PTR [ebp-4], eax
	mov	eax, DWORD PTR [ebp-4]
	leave
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 10.3.1 20210422 (Red Hat 10.3.1-1)"
	.section	.note.GNU-stack,"",@progbits
