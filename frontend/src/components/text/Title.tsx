import Params from "../../Params";

function Title(props: { children: any, isLight?: boolean }) {
    let color = props.isLight ? Params.colors.lightText : Params.colors.darkText;

    return (
        <h1 style={{color: color}}>
            {props.children}
        </h1>
    );
}

export default Title;