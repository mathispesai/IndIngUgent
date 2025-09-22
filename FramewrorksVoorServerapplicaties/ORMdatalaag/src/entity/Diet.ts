import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn } from "typeorm";
import { AppUser } from "./AppUser";

@Entity()
export class Diet {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    calorie_intake: number;

    @Column()
    protein_intake_grams: number;


    @OneToOne(() => AppUser, (appUser) => appUser.diet)
    @JoinColumn()
    appUser: AppUser;
}

