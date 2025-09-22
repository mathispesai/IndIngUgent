# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/364832961

def is_isbn13(isbn: str) -> bool:
    voorwaarde = False
    if isinstance(isbn, str) and isbn.isdigit():

        oneven = isbn[:12:2]
        som_oneven = 0
        for char in oneven:
            som_oneven += int(char)

        even = isbn[1:12:2]
        som_even = 0
        for char in even:
            som_even += int(char)

        if int(isbn[12]) == (10 -(som_oneven + 3 * som_even) % 10) % 10:
            voorwaarde = True

    return voorwaarde

