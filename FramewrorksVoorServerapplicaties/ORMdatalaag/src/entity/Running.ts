import { Entity, Column } from "typeorm";
import { Workout } from "./Workout";

@Entity()
export class Running extends Workout {
    @Column("float")
    distance: number;

    @Column({ nullable: true })
    pace?: number;
}