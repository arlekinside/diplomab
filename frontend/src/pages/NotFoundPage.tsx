import Params from "../Params";

function NotFoundPage() {

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            height: "50vh",
            width: "100vw"
        }}>
            <span style={{color: Params.colors.background, fontSize: '10vw'}}>
                404 :(
            </span>
        </div>
    );
}

export default NotFoundPage;