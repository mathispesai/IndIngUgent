import {Entity, PrimaryGeneratedColumn, Column, OneToMany, OneToOne, JoinTable, ManyToMany} from "typeorm";
import { Workout } from "./Workout";
import {Diet} from "./Diet";

@Entity()
export class AppUser {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    firstName: string

    @Column()
    lastName: string

    @Column()
    age: number

    @OneToMany(() => Workout, workout => workout.appUser, { cascade: true })
    workouts: Promise<Workout[]>;

    @OneToOne(() => Diet, diet => diet.appUser, { cascade: true, eager:true})
    diet: Promise<Diet>;

    @ManyToMany(() => AppUser)
    @JoinTable()
    friends: AppUser[];
}
