export default class Punt2D{
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }

    afstandTot(p) {
        return (this.x - p.x)*(this.x - p.x) + (this.y - p.y)*(this.y - p.y)
    }
}