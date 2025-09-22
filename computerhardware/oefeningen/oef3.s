	.file	"test.c"
	.intel_syntax noprefix
	.text
	.globl	f2
	.type	f2, @function
f2:
	push	ebp
	mov	ebp, esp
	mov	eax, DWORD PTR [ebp+8]
	mov	eax, DWORD PTR [eax]
	and	eax, 1
	test	eax, eax
	sete	al
	movzx	eax, al
	pop	ebp
	ret
	.size	f2, .-f2
	.globl	f
	.type	f, @function
f:
	push	ebp
	mov	ebp, esp
	sub	esp, 16
	mov	DWORD PTR [ebp-4], 0
	mov	DWORD PTR [ebp-8], 0
	jmp	.L4
.L6:
	mov	eax, DWORD PTR [ebp-8]
	lea	edx, [0+eax*4]
	mov	eax, DWORD PTR [ebp+8]
	add	eax, edx
	push	eax
	call	f2
	add	esp, 4
	test	eax, eax
	je	.L5
	mov	eax, DWORD PTR [ebp-8]
	lea	edx, [0+eax*4]
	mov	eax, DWORD PTR [ebp+8]
	lea	ecx, [edx+eax]
	mov	eax, DWORD PTR [ebp-4]
	lea	edx, [eax+1]
	mov	DWORD PTR [ebp-4], edx
	lea	edx, [0+eax*4]
	mov	eax, DWORD PTR [ebp+16]
	add	edx, eax
	mov	eax, DWORD PTR [ecx]
	mov	DWORD PTR [edx], eax
.L5:
	add	DWORD PTR [ebp-8], 1
.L4:
	mov	eax, DWORD PTR [ebp-8]
	cmp	eax, DWORD PTR [ebp+12]
	jl	.L6
	mov	eax, DWORD PTR [ebp-4]
	leave
	ret
	.size	f, .-f
	.section	.rodata
.LC0:
	.string	"%d "
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
	sub	esp, 52
	mov	DWORD PTR [ebp-36], 1
	mov	DWORD PTR [ebp-32], 2
	mov	DWORD PTR [ebp-28], 4
	mov	DWORD PTR [ebp-24], 7
	mov	DWORD PTR [ebp-20], 8
	lea	eax, [ebp-56]
	push	eax
	push	5
	lea	eax, [ebp-36]
	push	eax
	call	f
	add	esp, 12
	mov	DWORD PTR [ebp-16], eax
	mov	DWORD PTR [ebp-12], 0
	jmp	.L9
.L10:
	mov	eax, DWORD PTR [ebp-12]
	mov	eax, DWORD PTR [ebp-56+eax*4]
	sub	esp, 8
	push	eax
	push	OFFSET FLAT:.LC0
	call	printf
	add	esp, 16
	add	DWORD PTR [ebp-12], 1
.L9:
	mov	eax, DWORD PTR [ebp-12]
	cmp	eax, DWORD PTR [ebp-16]
	jl	.L10
	mov	eax, 0
	mov	ecx, DWORD PTR [ebp-4]
	leave
	lea	esp, [ecx-4]
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 10.3.1 20210422 (Red Hat 10.3.1-1)"
	.section	.note.GNU-stack,"",@progbits
