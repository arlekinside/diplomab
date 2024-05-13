import Params from "../../Params";

function Label(props: { children: any, isLight?: boolean }) {
    let color = props.isLight ? Params.colors.lightText : Params.colors.darkText;

    return (
        <span style={{
            color: color
        }}>
            {props.children}
        </span>
    )
}

export default Label;
