# https://dodona.ugent.be/nl/courses/1005/series/11151/activities/1000003050
def is_isbn(isbn: str) -> bool:
    geldig = False
    tellen = 1
    som = 0
    if isinstance(isbn, str):
        gegeven_controlegetal = isbn[9]
        isbn = isbn[:9]
        if isbn.isnumeric() and len(isbn) == 9: 
            for char in isbn:
                som += int(char) * tellen
                tellen += 1
            berekend_controlegetal = som % 11
            if str(berekend_controlegetal) == gegeven_controlegetal:
                geldig = True
            elif gegeven_controlegetal == "X" and berekend_controlegetal == 10:
                geldig = True
    return geldig
        

