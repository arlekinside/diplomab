import Params from "../../Params";

function LightTitle(props: { children: any}) {
    return (
        <h1 style={{color: Params.colors.secondary}}>
            {props.children}
        </h1>
    );
}

export default LightTitle;