import { Button } from "@/components/ui/button"
import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Link } from "react-router"

export function RegisterForm() {
    return (
        <Card className="w-full max-w-xl">
            <CardHeader>
                <CardTitle>Create a new user account</CardTitle>
                <CardDescription>
                    Please complete the required information.
                </CardDescription>
                <CardAction>
                    <Link to="/login">
                        <Button variant="link">Sign In</Button>
                    </Link>
                </CardAction>
            </CardHeader>
            <CardContent>
                <form>
                    <div className="flex flex-col gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="email">Email</Label>
                            <Input
                                id="email"
                                type="email"
                                placeholder="m@example.com"
                                required
                            />
                        </div>
                        <div className="grid gap-2">
                            <div className="flex items-center">
                                <Label htmlFor="">Password</Label>
                            </div>
                            <Input id="password" type="password" placeholder="**********" required />
                        </div>
                        <div className="grid gap-2">
                            <div className="flex items-center">
                                <Label htmlFor="password">Confirm Password</Label>
                            </div>
                            <Input id="confirm-password" type="password" placeholder="**********" required />
                        </div>
                    </div>
                </form>
            </CardContent>
            <CardFooter className="flex-col gap-2">
                <Button type="submit" className="w-full cursor-pointer">
                    Register
                </Button>
            </CardFooter>
        </Card>
    )
}
