import {AppDataSource} from "./data-source";
import {AppUser} from "./entity/AppUser";
import {Diet} from "./entity/Diet";
import {Workout} from "./entity/Workout";
import {Running} from "./entity/Running";
import {Repository} from "typeorm";

export class DAO {
    private appUserRepository: Repository<AppUser>;
    private dietRepository: Repository<Diet>;
    private workoutRepository: Repository<Workout>;
    private runningRepository: Repository<Running>;

    constructor() {
        this.appUserRepository = AppDataSource.getRepository(AppUser);
        this.dietRepository = AppDataSource.getRepository(Diet);
        this.workoutRepository = AppDataSource.getRepository(Workout);
        this.runningRepository = AppDataSource.getRepository(Running);
    }

    //getAll...
    async getAllAppUsers(){
        return await this.appUserRepository.find();
    }
    async getAllDiets(){
        return await this.dietRepository.find();
    }
    async getAllWorkouts(){
        return await this.workoutRepository.find();
    }
    async getAllRunningWorkouts(){
        return await this.runningRepository.find();
    }


    //get...
    async getAppUser(appUserId :number){
        return await this.appUserRepository.createQueryBuilder("app_user").where("app_user.id = :id", { id: appUserId }).getOne();
    }
    async getDiet(dietId :number){
        return await this.dietRepository.createQueryBuilder("diet").where("diet.id = :id", { id: dietId }).getOne();
    }
    async getWorkout(workoutId :number){
        return await this.workoutRepository.createQueryBuilder("workout").where("workout.id = :id", { id: workoutId }).getOne();
    }
    async getRunningWorkout(runningId :number){
        return await this.runningRepository.createQueryBuilder("running").where("running.id = :id", { id: runningId }).getOne();
    }


    //get...FromAppUser
    async getFriendsFromAppUser(appUserId: number) {
        return await this.appUserRepository.createQueryBuilder("app_user")
            .innerJoin("app_user_friends_app_user", "friendship", "friendship.appUserId_2 = app_user.id")
            .where("friendship.appUserId_1 = :id", { id: appUserId }).getMany();
    }
    async getDietFromAppUser(appUserId :number){
        return await this.dietRepository.createQueryBuilder("diet").where("diet.appUserId = :id", { id: appUserId }).getOne();
    }
    async getWorkoutsFromAppUser(appUserId :number){
        return await this.workoutRepository.createQueryBuilder("workout").where("workout.appUserId = :id", { id: appUserId }).getMany();
    }
    async getRunningWorkoutsFromAppUser(appUserId :number){
        return await this.runningRepository.createQueryBuilder("running").where("running.appUserId = :id", { id: appUserId }).getMany();
    }


    //set...
    async setAppUser(firstName :string, lastName :string, age :number){
        await this.appUserRepository.createQueryBuilder().insert().into(AppUser)
            .values([{ firstName: firstName, lastName: lastName, age: age}]).execute()
    }
    async setDiet(calories :number, protein :number, appUser : AppUser){
        await this.dietRepository.createQueryBuilder().insert().into(Diet)
            .values([{ calorie_intake: calories, protein_intake_grams: protein, appUser: appUser}]).execute()
    }
    async setWorkout(type: string, duration_minutes: number, date: Date, appUser : AppUser){
        await this.workoutRepository.createQueryBuilder().insert().into(Workout)
            .values([{type: type, duration_minutes: duration_minutes, date: date, appUser: appUser}]).execute()
    }
    async setRunningWorkout(type: string, duration_minutes: number, date: Date, distance:number , pace:number, appUser : AppUser){
        await this.runningRepository.createQueryBuilder().insert().into(Running)
            .values([{type: type, duration_minutes: duration_minutes, date: date,distance: distance, pace: pace , appUser: appUser}]).execute()
    }


    //set... (plural)
    async setAppUsers(appusers: AppUser[]){
        for(const appuser of appusers){
            await this.setAppUser(appuser.firstName, appuser.lastName, appuser.age);
        }
    }
    async setWorkouts(workouts: Workout[], appUser: AppUser){
        for(const workout of workouts){
            await this.setWorkout(workout.type, workout.duration_minutes, workout.date, appUser);
        }
    }
    async setRunningWorkouts(runnings: Running[], appUser:AppUser){
        for(const running of runnings){
            await this.setRunningWorkout(running.type, running.duration_minutes, running.date, running.distance, running.pace, appUser);
        }
    }


    //update... (gebruikt id van table)
    async updateAppUser(appUserId :number, firstName ?:string, lastName ?:string, age ?:number){
        const updateData: Partial<AppUser> = {};
        if (firstName !== undefined) updateData.firstName = firstName;
        if (lastName !== undefined) updateData.lastName = lastName;
        if (age !== undefined) updateData.age = age;

        await this.appUserRepository.createQueryBuilder().update(AppUser)
            .set(updateData).where("id = :id", { id: appUserId }).execute()
    }
    async updateDiet(dietId :number, calories ?:number, protein ?:number){
        const updateData: Partial<Diet> = {};
        if (calories !== undefined) updateData.calorie_intake = calories;
        if (protein !== undefined) updateData.protein_intake_grams = protein;

        await this.dietRepository.createQueryBuilder().update(Diet)
            .set(updateData).where("id = :id", { id: dietId }).execute()
    }
    async updateWorkout(workoutId :number, type?: string, duration_minutes?: number, date?: Date){
        const updateData: Partial<Workout> = {};
        if (type !== undefined) updateData.type = type;
        if (duration_minutes !== undefined) updateData.duration_minutes = duration_minutes;
        if (date !== undefined) updateData.date = date;

        await this.workoutRepository.createQueryBuilder().update(Workout)
            .set(updateData).where("id = :id", { id: workoutId }).execute()
    }
    async updateRunningWorkout(workoutId :number, type?: string, duration_minutes?: number, date?: Date, distance?:number , pace?:number){
        const updateData: Partial<Running> = {};
        if (type !== undefined) updateData.type = type;
        if (duration_minutes !== undefined) updateData.duration_minutes = duration_minutes;
        if (date !== undefined) updateData.date = date;
        if (type !== undefined) updateData.distance = distance;
        if (duration_minutes !== undefined) updateData.pace = pace;

        await this.runningRepository.createQueryBuilder().update(Running)
            .set(updateData).where("id = :id", { id: workoutId }).execute()
    }


    //update...FromAppUser (gebruikt id van AppUser)
    async updateDietFromAppUser(appUserId :number, calories ?:number, protein ?:number){
        const updateData: Partial<Diet> = {};
        if (calories !== undefined) updateData.calorie_intake = calories;
        if (protein !== undefined) updateData.protein_intake_grams = protein;

        await this.dietRepository.createQueryBuilder().update(Diet)
            .set(updateData).where("appUserId = :id", { id: appUserId }).execute()
    }


    //delete...
    async deleteAppUser(appUserId :number){
        await this.appUserRepository.createQueryBuilder().delete().from(AppUser)
            .where("id = :id", { id: appUserId }).execute()
    }
    async deleteDiet(dietId :number){
        await this.dietRepository.createQueryBuilder().delete().from(Diet)
            .where("id = :id", { id: dietId }).execute()
    }
    async deleteWorkout(workoutId :number){
        await this.workoutRepository.createQueryBuilder().delete().from(Workout)
            .where("id = :id", { id: workoutId }).execute()
    }
    async deleteRunning(runningId :number){
        await this.runningRepository.createQueryBuilder().delete().from(Running)
            .where("id = :id", { id: runningId }).execute()
    }


    //vriendschappen toevoegen en verwijderen
    async setFriends(appuser1 :AppUser, appuser2 : AppUser){
        await this.appUserRepository.createQueryBuilder().relation(AppUser,"friends").of(appuser1).add(appuser2);
        await this.appUserRepository.createQueryBuilder().relation(AppUser,"friends").of(appuser2).add(appuser1);
    }
    async deleteFriends(appuser1 :AppUser, appuser2 : AppUser) {
        await this.appUserRepository.createQueryBuilder().relation(AppUser,"friends").of(appuser1).remove(appuser2);
        await this.appUserRepository.createQueryBuilder().relation(AppUser,"friends").of(appuser2).remove(appuser1);
    }


    //parameteropvraging
    async getUsersByFirstname(name: string){
        return await this.appUserRepository.createQueryBuilder("app_user").where("app_user.firstName = :firstName", { firstName: name}).getMany();
    }
    async getUsersByLastname(name: string){
        return await this.appUserRepository.createQueryBuilder("app_user").where("app_user.lastName = :lastName", { lastName: name }).getMany();
    }
    async getUsersByAge(age: number){
        return await this.appUserRepository.createQueryBuilder("app_user").where("app_user.age = :age", { age: age }).getMany();
    }
}