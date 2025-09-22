import {Entity, PrimaryGeneratedColumn, Column, ManyToOne} from "typeorm"
import {AppUser} from "./AppUser";

@Entity()
export class Workout {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    type: string

    @Column()
    duration_minutes: number

    @Column()
    date: Date

    @ManyToOne(() => AppUser, appUser => appUser.workouts)
    appUser: AppUser;
}