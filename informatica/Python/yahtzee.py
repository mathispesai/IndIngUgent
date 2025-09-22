# https://dodona.ugent.be/nl/courses/1005/series/11160/activities/1338828255
def som(stenen:list)->int:
    som = 0
    for item in stenen:
        som += item
    return som

def is_Yathzee(stenen:list)->bool:
    return stenen.count(stenen[0]) == len(stenen)

def partition(l, r, nums):
    # Last element will be the pivot and the first element the pointer
    pivot, ptr = nums[r], l
    for i in range(l, r):
        if nums[i] <= pivot:
            # Swapping values smaller than the pivot to the front
            nums[i], nums[ptr] = nums[ptr], nums[i]
            ptr += 1
    # Finally swapping the last element with the pointer indexed number
    nums[ptr], nums[r] = nums[r], nums[ptr]
    return ptr

def quicksort(l, r, nums):
    if len(nums) == 1:  # Terminating Condition for recursion. VERY IMPORTANT!
        return nums
    if l < r:
        pi = partition(l, r, nums)
        quicksort(l, pi-1, nums)  # Recursively sorting the left values
        quicksort(pi+1, r, nums)  # Recursively sorting the right values
    return nums

def is_grote_straat(stenen:list)->bool:
    gesorteerd = quicksort(0, len(stenen) - 1, stenen)
    teller = 1
    lijst = [] 
    while teller < (len(stenen) + 1):
        lijst.append(teller)
        teller += 1
    return gesorteerd == lijst