import "reflect-metadata"
import { DataSource } from "typeorm"
import { AppUser } from "./entity/AppUser"
import {Workout} from "./entity/Workout";
import {Diet} from "./entity/Diet";
import {Running} from "./entity/Running";

export const AppDataSource = new DataSource({
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "postgres",
    password: "password",
    database: "postgres",
    synchronize: true,
    logging: true,
    entities: [AppUser, Workout, Diet, Running],
    migrations: [],
    subscribers: [],
})
