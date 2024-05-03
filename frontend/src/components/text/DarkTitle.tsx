import Params from "../../Params";

function DarkTitle(props: { children: any}) {
    return (
        <h1 style={{color: Params.colors.main}}>
            {props.children}
        </h1>
    );
}

export default DarkTitle;