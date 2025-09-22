	.file	"testc.c"
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
.L3:
	mov	edx, DWORD PTR [ebp+12]
	lea	eax, [edx+1]
	mov	DWORD PTR [ebp+12], eax
	mov	eax, DWORD PTR [ebp+8]
	lea	ecx, [eax+1]
	mov	DWORD PTR [ebp+8], ecx
	movzx	edx, BYTE PTR [edx]
	mov	BYTE PTR [eax], dl
.L2:
	mov	eax, DWORD PTR [ebp+12]
	movzx	eax, BYTE PTR [eax]
	test	al, al
	jne	.L3
	mov	eax, DWORD PTR [ebp+8]
	mov	BYTE PTR [eax], 0
	nop
	leave
	ret
	.size	f, .-f
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
	mov	DWORD PTR [ebp-13], 1953719668
	mov	BYTE PTR [ebp-9], 0
	lea	eax, [ebp-13]
	push	eax
	lea	eax, [ebp-18]
	push	eax
	call	f
	add	esp, 8
	sub	esp, 12
	lea	eax, [ebp-18]
	push	eax
	call	puts
	add	esp, 16
	mov	eax, 0
	mov	ecx, DWORD PTR [ebp-4]
	leave
	lea	esp, [ecx-4]
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 10.3.1 20210422 (Red Hat 10.3.1-1)"
	.section	.note.GNU-stack,"",@progbits
